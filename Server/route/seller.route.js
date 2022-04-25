import orderModel from '../model/order.model.js'
import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/pending', async (req, res) => {
	const seller = req.query.seller,
		orders = await orderModel.findByStatus('Pending')
	let response = []

	for (let order of orders) {
		let products = []
		for (let product of order.product) {
			const book = await bookModel.findBookWSeller(seller, product._id)
			products.push({
				_id: book._id,
				name: book.name,
				picture: book.picture,
				price: book.saleprice == null ? book.price : book.saleprice,
				quantity: product.quantity,
			})
		}
		const temp = { ...order }
		temp._doc.product = products
		response.push(temp._doc)
	}

	res.send({ orders: response })
})

export default router
