import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'
import multer from '../utils/multer.js'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/onsale', async (req, res) => {
	try {
		const ret = await bookModel.getBookOnSale()
		res.send({ arrBookOnSale: ret })
	} catch (err) {
		console.log(err)
		res.send({
			exitcode: 500,
		})
	}
})

router.post('/add', multer.single('image'), async (req, res) => {
	try {
		const ret = await bookModel.addBook(req.file, req.body)
		res.send({
			exitcode: 0,
			detail: ret,
		})
	} catch (error) {
		console.log(error)
		res.send({
			exitcode: 500,
		})
	}
})

router.post('/getDetails', async (req, res) => {
	try {
		const ret = await bookModel.getBook(req.body._id)
		const relatedBooks = await bookModel.getBookByCategory(ret.category, ret._id)
		res.send({
			_id: ret._id,
			name: ret.name,
			author: ret.author,
			distributor: ret.distributor,
			seller: ret.seller,
			price: ret.price,
			saleprice: ret.saleprice,
			category: ret.category,
			picture: ret.picture,
			release_year: ret.release_year,
			description: ret.description,
			quantity: ret.quantity,
			state: ret.state,
			star: ret.star,
			comments: ret.comments,
			relatedBooks: relatedBooks,
		})
	} catch (err) {
		console.log(err)
		res.send({
			exitcode: 500,
		})
	}
})

router.get('/', async (req, res) => {
	try {
		const ret = await bookModel.findAll()
		res.send({ arrBook: ret })
	} catch (error) {
		console.log(error)
		res.send({
			exitcode: 500,
		})
	}
})


export default router
