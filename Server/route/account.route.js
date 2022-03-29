import bcrypt from 'bcrypt';
import crypto from 'crypto';
import accountModel from '../model/account.model.js';
import transporter from '../utils/nodemailer..js';
import dotenv from 'dotenv';
const salt = 10;

dotenv.config()

function assignRoutes(app) {
    app.post('/account/login', async (req, res) => {
        const account = await accountModel.findByEmail(req.body.email);

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

        const ret = bcrypt.compareSync(req.body.password, account.password);
        if (ret === false || account.emailToken !== null) {
            res.send({
                "exitcode": 104
            });
            return;
        }

        res.send({
            "exitcode": 0,
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
                    <h4 style="text-align: center;">Please click <a href="http://${req.headers.host}/account/verify/${emailToken}">here</a> to activate your account</h4>
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

    app.get('/account/verify', async (req, res) => {
        const { token } = req.params;

        const account = await accountModel.findByEmail(email);

        await accountModel.activateAccount(token);

        res.send({
            "exitcode": 0
        })
    });

    app.get('/account/verify/:token', async (req, res) => {
        const { token } = req.params;

        await accountModel.activateAccount(token);

        res.end()
    });
}

export default {
    assignRoutes
}