import mongoose from 'mongoose'
import config from '../config/config.js'
import accountModel from './account.model.js'
import bookModel from './book.model.js'
import cartModel from './cart.model.js'

const url = config.url

mongoose.connect(url, {
	useNewUrlParser: true,
	useUnifiedTopology: true,
})

const OrderSchema = mongoose.Schema({
	email: {
		type: String,
	},
	product: [
		{
			_id: { type: String },
			quantity: { type: Number },
			price: { type: Number },
		},
	],
	status: { type: String },
	date: { type: Date },
	total: {
		type: Number,
	},
	phone: { type: String },
	address: { type: String },
	receiver: { type: String },
})

const Order = mongoose.model('order', OrderSchema, 'order')

export default {
	createOrder: async (email, data, phone, address, receiver) => {
		try {
			const user = await accountModel.findByEmail(email)
			if (phone === '') {
				phone = user.phone
			}
			if (address === '') {
				address = user.address
			}
			if (receiver === '') {
				receiver = user.email
			}
			let total = 0
			let result = []
			for (let i = 0; i < data.length; i++) {
				total += data[i].price * data[i].quantity
				let book = await bookModel.getBook(data[i]._id)
				if (data[i].quantity < book.quantity) {
					result.push(data[i])
					await bookModel.updateQuantity(data[i]._id, book.quantity - data[i].quantity)
					await cartModel.DeleteItem(email, data[i]._id)
				} else {
					return null
				}
			}
			total += 30000
			const newCheckout = {
				email: email,
				product: result,
				status: 'Pending',
				date: new Date(),
				total: total,
				phone: phone,
				address: address,
				receiver: receiver,
			}
			console.log(newCheckout)

			return await Order.create(newCheckout)
		} catch (error) {
			console.log(error)
			return null
		}
	},
	async getOrder(email, status) {
		try {
			const order = await Order.find({ email: email, status: status }).lean()
			return order
		} catch (err) {
			console.log(err)
		}
	},
	async cancelOrder(orderId) {
		try {
			await Order.findOneAndUpdate(
				{ _id: orderId },
				{
					$set: { status: 'Canceled' },
				}
			)
		} catch (err) {
			console.log(err)
		}
	},
	findAll: async () => {
		return (await Order.find({}).lean()) || null
	},
	findByStatus: async (status) => {
		return await Order.find({ status: status })
	},
}
