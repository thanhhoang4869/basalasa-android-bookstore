// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const UserSchema = new mongoose.Schema({
    username: { type: String, require: true, unique: true },
    password: { type: String, require: true },
    email: { type: String, require: true },
    otp: Number,
    dob: Date,
    address: String,
    phone: String,
    role: Number
})

const Account = mongoose.model('user', UserSchema, 'user')

export default {
    async findByUsername(username) {
        console.log(username)
        const ret = await Account.find({
            username: username
        })

        console.log(ret[0])

        return ret[0] || null
    }
}