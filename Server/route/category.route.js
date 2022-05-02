import categoryModel from '../model/category.model.js'
import bodyParser from 'body-parser'
import express from 'express'
import bookModel from '../model/book.model.js'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.get('/', async (req, res) => {
	try {
		const ret = await categoryModel.getCategory()
		res.send({ arrCategory: ret })
	} catch (err) {
		console.log(err)
		res.send({
			exitcode: 500,
		})
	}
})

router.get('/details', async (req, res) => {
	const { category } = req.query
	const books = await bookModel.findBookWCategory(category)
	res.send({ arrBook: books })
})

export default router
