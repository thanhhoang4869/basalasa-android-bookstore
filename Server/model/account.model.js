// import accountModel from './account.schema.js'

import mongoose from 'mongoose'

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

const Account = mongoose.model('user', UserSchema)

export default {
    async findByUsername(username) {
        const ret = await Account.find({
            username: username
        })

        return ret[0] || null
    }
}