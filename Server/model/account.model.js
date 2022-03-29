// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const UserSchema = new mongoose.Schema({
    password: { type: String, require: true },
    email: { type: String, require: true, unique: true },
    otp: Number,
    address: String,
    phone: String,
    role: Number,
    fullName: String,
    status: Boolean,
    emailToken: String,
})

const Account = mongoose.model('user', UserSchema, 'user')

export default {
    async findByEmail(email) {
        const ret = await Account.findOne({
            email: email
        }).lean();

        return ret || null
    },
    async checkEmail(email) {
        const r = await Account.findOne({
            email: email
        }).lean();
        return r === null ? false : true
    },
    async create(user) {
        const ret = await Account.create(user);
        return ret
    },
    async activateAccount(token) {
        try {
            await Account.findOneAndUpdate({ emailToken: token }, {
                $set: { emailToken: null }
            })
            return true
        } catch (err) {
            return false
        }
    }

}