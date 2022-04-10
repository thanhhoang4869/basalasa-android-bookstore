import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const OrderSchema = new mongoose.Schema({
    email: { type: String, required: true },
    product: { type: Array, required: true },
    status: { type: String, required: true },
    date: { type: Date, required: true },
    total: { type: Number, required: true },
})
const Order = mongoose.model('order', OrderSchema, 'order')

export default {
    async getOrder(email, status) {
        try {
            const order = await Order.find({ email: email, status: status }).lean()
            return order
        } catch (err) {
            console.log(err)
        }
    }
}