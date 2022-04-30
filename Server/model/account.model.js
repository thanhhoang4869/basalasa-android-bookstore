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
    address: String,
    phone: String,
    role: Number,
    fullName: String,
    status: Number,
    emailToken: String,
    request: Number
})

const Account = mongoose.model('user', UserSchema, 'user')

export default {
    async findAll() {
        const ret = await Account.find({ role: { $ne: 2 } }).sort({ role: "desc" }).lean();
        return ret || null
    },

    async findByEmail(email) {
        const ret = await Account.findOne({
            email: email
        }).lean();
        return ret || null
    },

    async findRequest() {
        const ret = await Account.find({
            request: 1
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
            console.log(err)
            return false
        }
    },
    async generateNewPassword(email, newPass) {
        try {
            await Account.findOneAndUpdate({ email: email }, {
                $set: { password: newPass }
            })
        } catch (err) {
            console.log(err)
        }
    },
    async updateAccount(email, user) {
        // console.log(user)
        try {
            await Account.findOneAndUpdate({ email: email }, {
                $set: user
            })
        } catch (err) {
            console.log(err)
        }
    },

    // async updateRequest(email, reqState) {
    //     console.log(user)
    //     try {
    //         await Account.findOneAndUpdate({ email: email }, {
    //             $set: { request: reqState }
    //         })
    //     } catch (err) {
    //         console.log(err)
    //     }
    // }
}