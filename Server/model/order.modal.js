// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

OrderSchema = new mongoose.Schema({
    email: { type: String, required: true },
    product: { type: Array, required: true },
    status: { type: String, required: true },
    date: { type: Date, required: true },
})
Order = mongoose.model('order', OrderSchema, 'order')

export default {

}