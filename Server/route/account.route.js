import bcrypt from 'bcrypt';
import crypto from 'crypto';
import accountModel from '../model/account.model.js';
import transporter from '../utils/nodemailer..js';
import dotenv from 'dotenv';
import config from '../config/config.js'
import jwt from 'jsonwebtoken'
const salt = 10;

dotenv.config()

function assignRoutes(app) {
    app.post('/account/login', async (req, res) => {
        console.log('dag login')
        const account = await accountModel.findByEmail(req.body.email);

        if (account === null) {
            res.send({
                "exitcode": 104,
                token: '',
            });
            return;
        }

        if (account.status) {
            res.send({
                "exitcode": 708, //blocked
                token: '',
            });
            return;
        }

        const ret = bcrypt.compareSync(req.body.password, account.password);
        if (ret === false || account.emailToken !== null) {
            res.send({
                "exitcode": 104,
                token: '',
            });
            return;
        }

        const payload = {
            email: account.email,
        }

        res.send({
            "exitcode": 0,
            token: jwt.sign(payload, config.server.secret, {
                expiresIn: config.server.expTime
            }),
            message: "Login successfully"
        });
    });

    app.post('/account/getAccount', async (req, res) => {
        const data = {
            email: req.payload.email
        }

        const account = await accountModel.findByEmail([data.email]);

        if (account === null) {
            res.send({
                "exitcode": 104
            });
            return;
        }

        if (account.status) {
            res.send({
                "exitcode": 708 //blocked
            });
            return;
        }

        res.send({
            "exitcode": 0,
            "email": account.email,
            "name": account.name,
            "phone": account.phone,
            "address": account.address,
            "role": account.role
        });
    });

    app.post('/account/register', async (req, res) => {
        const { email, password, fullName, phone, address } = req.body;

        const usedEmail = await accountModel.checkEmail(email);

        if (usedEmail) {
            res.send({
                "exitcode": 701 //email has been already used
            });
            return;
        }

        const hashPassword = await bcrypt.hash(password, salt);
        const emailToken = crypto.randomBytes(20).toString('hex');;

        const mailOption = {
            from: process.env.SHOP_GMAIL_USERNAME,
            to: email,
            subject: 'Email verification',
            html: `<div style="background-color: #ACD1AF; padding: 2em 2em;">
                    <h1 style="text-align: center;">Thank you for registering</h1>
                    <h3 style="text-align: center;">Please click <a href="http://${req.headers.host}/account/verify/${emailToken}">here</a> to activate your account</h3>
                </div>`
        }

        transporter.sendMail(mailOption, function (err, info) {
            if (err) console.log(err);
        })

        await accountModel.create({
            email: email,
            password: hashPassword,
            fullName: fullName.trim(),
            phone: phone.trim(),
            address: address.trim(),
            otp: null,
            emailToken: emailToken,
            role: 0,
            status: false
        })

        res.send({
            "exitcode": 0
        })
    });

    app.get('/account/verify/:token', async (req, res) => {
        const { token } = req.params;

        await accountModel.activateAccount(token);

        res.send("Activate successfully")
    });

    app.post('/account/forget', async (req, res) => {
        const { email } = req.body;

        const account = await accountModel.findByEmail(email)

        if (account) {
            const newPass = (Math.random() + 1).toString(36).substring(2);
            account.password = await bcrypt.hash(newPass, salt);

            const mailOption = {
                from: process.env.SHOP_GMAIL_USERNAME,
                to: email,
                subject: 'Forget password',
                html: `<div style="background-color: #ACD1AF; padding: 2em 2em;">
                        <h1 style="text-align: center;">This is your new password</h1>
                        <h3 style="text-align: center;">${newPass}</h3>
                    </div>`
            }

            transporter.sendMail(mailOption, function (err, info) {
                if (err) console.log(err);
            })

            await accountModel.generateNewPassword(email, account.password)

            res.send({
                "exitcode": 0
            })

            return;
        }

        res.send({
            "exitcode": 500
        })
    })
}

export default {
    assignRoutes
}