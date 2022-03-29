// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const UserSchema = new mongoose.Schema({
    email: { type: String, require: true, unique: true },
    password: { type: String, require: true },
    otp: Number,
    dob: Date,
    fullName: String,
    address: String,
    phone: String,
    role: Number,
    status: Boolean
})

const Account = mongoose.model('user', UserSchema, 'user')

export default {
    async findByEmail(email) {
        const ret = await Account.findOne({
            email: email
        }).lean();

        return ret || null
    }
}