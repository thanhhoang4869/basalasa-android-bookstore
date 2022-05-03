import crypto from 'crypto'
import accountModel from '../model/account.model.js'
import orderModel from '../model/order.model.js'
import bookModel from '../model/book.model.js'
import transporter from '../utils/nodemailer..js'
import dotenv from 'dotenv'
import bodyParser from 'body-parser'
import express from 'express'
import config from '../config/config.js'
import jwt from 'jsonwebtoken'
import { sha256 } from 'js-sha256'
import cartModel from '../model/cart.model.js'
const salt = 10

dotenv.config()

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.post('/login', async(req, res) => {
    const account = await accountModel.findByEmail(req.body.email)

    if (account === null) {
        res.send({
            exitcode: 104,
            token: '',
        })
        return
    }

    if (!account.status) {
        res.send({
            exitcode: 708, //blocked
            token: '',
        })
        return
    }

    const ret = req.body.password === account.password
    console.log(account.password)
    console.log(req.body.password === account.password)
    if (ret === false || account.emailToken !== null) {
        res.send({
            exitcode: 104,
            token: '',
        })
        return
    }

    const payload = {
        email: account.email,
    }

    res.send({
        exitcode: 0,
        token: jwt.sign(payload, config.server.secret, {
            expiresIn: config.server.expTime,
        }),
        message: 'Login successfully',
    })
})

router.get('/getAccount', async(req, res) => {
    const data = {
        email: req.payload.email,
    }

    const account = await accountModel.findByEmail(data.email)

    if (account === null) {
        res.send({
            exitcode: 104,
        })
        return
    }

    if (!account.status) {
        res.send({
            exitcode: 708, //blocked
        })
        return
    }

    res.send({
        exitcode: 0,
        email: account.email,
        fullName: account.fullName,
        phone: account.phone,
        address: account.address,
        role: account.role,
        status: account.status,
        request: account.request,
    })
})

router.post('/request', async(req, res) => {
    try {
        const email = req.payload.email
        const user = await accountModel.findByEmail(email)
        user.request = 1

        await accountModel.updateAccount(email, user)

        res.send({
            exitcode: 0,
            message: 'Request sent successfully',
        })
    } catch (err) {
        console.log(err)
        res.send({
            exitcode: 500,
        })
    }
})

router.post('/register', async(req, res) => {
    const { email, password, fullName, phone, address } = req.body

    const usedEmail = await accountModel.checkEmail(email)

    if (usedEmail) {
        res.send({
            exitcode: 701, //email has been already used
        })
        return
    }

    const emailToken = crypto.randomBytes(20).toString('hex')

    const mailOption = {
        from: process.env.SHOP_GMAIL_USERNAME,
        to: email,
        subject: 'Email verification',
        html: `<div style="background-color: #ACD1AF; padding: 2em 2em;">
                    <h1 style="text-align: center;">Thank you for registering</h1>
                    <h3 style="text-align: center;">Please click <a href="http://${req.headers.host}/account/verify/${emailToken}">here</a> to activate your account</h3>
                </div>`,
    }

    transporter.sendMail(mailOption, function(err, info) {
        if (err) console.log(err)
    })

    await accountModel.create({
        email: email,
        password: password,
        fullName: fullName.trim(),
        phone: phone.trim(),
        address: address.trim(),
        otp: null,
        emailToken: emailToken,
        role: 0,
        status: true,
    })

    await cartModel.CreateCart(email)

    res.send({
        exitcode: 0,
    })
})

router.get('/verify/:token', async(req, res) => {
    const { token } = req.params

    await accountModel.activateAccount(token)

    res.send('Activate successfully')
})

router.post('/forget', async(req, res) => {
    const { email } = req.body

    const account = await accountModel.findByEmail(email)

    if (account) {
        const newPass = (Math.random() + 1).toString(36).substring(2)
        account.password = await sha256(newPass)

        const mailOption = {
            from: process.env.SHOP_GMAIL_USERNAME,
            to: email,
            subject: 'Forget password',
            html: `<div style="background-color: #ACD1AF; padding: 2em 2em;">
                        <h1 style="text-align: center;">This is your new password</h1>
                        <h3 style="text-align: center;">${newPass}</h3>
                    </div>`,
        }

        transporter.sendMail(mailOption, function(err, info) {
            if (err) console.log(err)
        })

        await accountModel.generateNewPassword(email, account.password)

        res.send({
            exitcode: 0,
        })

        return
    }

    res.send({
        exitcode: 500,
    })
})

router.post('/changeInfo', async(req, res) => {
    const { email, fullName, phone, address } = req.body

    const user = await accountModel.findByEmail(email)

    user.fullName = fullName.trim()
    user.phone = phone.trim()
    user.address = address.trim()

    await accountModel.updateAccount(email, user)

    res.send({
        exitcode: 0,
    })
})

router.post('/changePass', async(req, res) => {
    const { oldPassword, newPassword } = req.body

    const data = {
        email: req.payload.email,
    }

    const account = await accountModel.findByEmail(data.email)

    const ret = oldPassword == account.password

    if (ret === false) {
        res.send({
            exitcode: 400,
        })
        return
    }

    account.password = newPassword

    await accountModel.updateAccount(data.email, account)

    res.send({
        exitcode: 0,
    })
})

router.post('/history', async(req, res) => {
    const { tab } = req.body

    const orders = await orderModel.getOrder(req.payload.email, tab)

    for (let i = 0; i < orders.length; i++) {
        for (let j = 0; j < orders[i].product.length; j++) {
            const bookInfo = await bookModel.getBook(orders[i].product[j]._id)
            orders[i].product[j].name = bookInfo.name
            orders[i].product[j].picture = bookInfo.picture
        }
        orders[i].date = orders[i].date.toLocaleDateString('vi-VN')
    }
    console.log(orders)
    res.send({ orders })
})

router.post('/history/delete', async(req, res) => {
    const { orderId } = req.body

    try {
        await orderModel.cancelOrder(orderId)
        res.send({
            exitcode: 0,
        })
    } catch (err) {
        console.log(err)
        res.send({
            exitcode: 500,
        })
    }
})

router.post('/history/confirm', async(req, res) => {
    const { orderId } = req.body

    try {
        await orderModel.confirmOrder(orderId)
        res.send({
            exitcode: 0,
        })
    } catch (err) {
        console.log(err)
        res.send({
            exitcode: 500,
        })
    }
})
router.post('/history/deliver', async(req, res) => {
    const { orderId } = req.body

    try {
        await orderModel.deliverOrder(orderId)
        res.send({
            exitcode: 0,
        })
    } catch (err) {
        console.log(err)
        res.send({
            exitcode: 500,
        })
    }
})
router.post('/history/done', async(req, res) => {
    const { orderId } = req.body

    try {
        await orderModel.doneOrder(orderId)
        res.send({
            exitcode: 0,
        })
    } catch (err) {
        console.log(err)
        res.send({
            exitcode: 500,
        })
    }
})

export default router