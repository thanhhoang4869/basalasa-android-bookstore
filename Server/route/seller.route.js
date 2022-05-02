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
		let products = [],
			flag = false
		for (let product of order.product) {
			const book = await bookModel.findBookWSeller(seller, product._id)
			if (book != null) {
				products.push({
					_id: book._id,
					name: book.name,
					picture: book.picture,
					price: book.saleprice == null ? book.price : book.saleprice,
					quantity: product.quantity,
				})
				flag = true
			}
		}
		if (flag) {
			const temp = { ...order }
			temp._doc.product = products
			response.push(temp._doc)
		}
	}
	res.send({ orders: response })
})

router.get('/processing', async (req, res) => {
	const seller = req.query.seller,
		orders1 = await orderModel.findByStatus('Preparing'),
		orders2 = await orderModel.findByStatus('Arriving'),
		orders = orders1.concat(orders2)
	let response = []

	for (let order of orders) {
		let products = [],
			flag = false
		for (let product of order.product) {
			const book = await bookModel.findBookWSeller(seller, product._id)
			if (book != null) {
				products.push({
					_id: book._id,
					name: book.name,
					picture: book.picture,
					price: book.saleprice == null ? book.price : book.saleprice,
					quantity: product.quantity,
				})
				flag = true
			}
		}
		if (flag) {
			const temp = { ...order }
			temp._doc.product = products
			response.push(temp._doc)
		}
	}
	res.send({ orders: response })
})

router.get('/completed', async (req, res) => {
	const seller = req.query.seller,
		orders = await orderModel.findByStatus('Completed')
	let response = []

	for (let order of orders) {
		let products = [],
			flag = false
		for (let product of order.product) {
			const book = await bookModel.findBookWSeller(seller, product._id)
			if (book != null) {
				products.push({
					_id: book._id,
					name: book.name,
					picture: book.picture,
					price: book.saleprice == null ? book.price : book.saleprice,
					quantity: product.quantity,
				})
				flag = true
			}
		}
		if (flag) {
			const temp = { ...order }
			temp._doc.product = products
			response.push(temp._doc)
		}
	}
	res.send({ orders: response })
})

router.get('/canceled', async (req, res) => {
	const seller = req.query.seller,
		orders = await orderModel.findByStatus('Canceled')
	let response = []

	for (let order of orders) {
		let products = [],
			flag = false
		for (let product of order.product) {
			const book = await bookModel.findBookWSeller(seller, product._id)
			if (book != null) {
				products.push({
					_id: book._id,
					name: book.name,
					picture: book.picture,
					price: book.saleprice == null ? book.price : book.saleprice,
					quantity: product.quantity,
				})
				flag = true
			}
		}
		if (flag) {
			const temp = { ...order }
			temp._doc.product = products
			response.push(temp._doc)
		}
	}
	res.send({ orders: response })
})

router.get('/delete', async (req, res) => {
	try {
		const book = await bookModel.deleteBook(req.query.id)
		if (book != null) {
			res.send({ error: false })
		} else {
			res.send({ error: true })
		}
	} catch (error) {
		res.send({ error: true })
	}
})

router.post('/update', async (req, res) => {
	const { id, author, description, distributor, quantity, saleprice, price } = req.body
	try {
		await bookModel.updateBook(id, author, description, distributor, quantity, saleprice, price)
		res.send({ error: false })
	} catch (error) {
		res.send({ error: true })
	}
})

export default router
