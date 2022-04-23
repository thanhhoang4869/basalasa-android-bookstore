import orderModel from '../model/order.model.js'
import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/pending', async (req, res) => {
	const seller = '19127489@student.hcmus.edu.vn'

	const orderList = await orderModel.findAll()
	const bookList = await bookModel.findAll()

	let results = []
	for (let order of orderList) {
		for (let book of bookList) {
			if (
				order.product[0].book_id == book.id &&
				book.seller == seller &&
				order.status == 'Pending'
			) {
				order.date = order.date.toLocaleDateString('vi-VN')
				order.product[0].picture = book.picture
				results.push(order)
			}
		}
	}
	console.log(results)
	res.send({ results: results })
})

export default router
