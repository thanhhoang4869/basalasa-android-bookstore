import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

let dump = ''

router.post('/filter', async (req, res) => {
	try {
		const ret = await bookModel.findAll()
		console.log(req.body)
		let { category, max, min } = req.body

		if (min == '') min = 0
		if (max == '') max = Number.MAX_SAFE_INTEGER
		if (min > max) {
			min = 0
			max = Number.MAX_SAFE_INTEGER
		}

		let temp = []
		let results = []
		ret.forEach((book) => {
			if (
				book.name.toLowerCase().includes(dump) ||
				book.author.toLowerCase().includes(dump) ||
				book.distributor.toLowerCase().includes(dump) ||
				book.category.toLowerCase().includes(dump)
			) {
				temp.push(book)
			}
		})
		if (category.length == 0) {
			temp.forEach((book) => {
				if (book.saleprice != null) {
					if (book.saleprice >= min && book.saleprice <= max) results.push(book)
				} else {
					if (book.price >= min && book.price <= max) results.push(book)
				}
			})
		} else {
			category.forEach((cate) => {
				temp.forEach((book) => {
					if (book.category.includes(cate)) results.push(book)
				})
			})
		}

		res.send({ filterResults: results })
	} catch (error) {
		console.log(error)
		res.send({
			exitcode: 500,
		})
	}
})

router.post('/', async (req, res) => {
	try {
		const ret = await bookModel.findAll()
		const input = req.body.input.toLowerCase()
		dump = input
		let results = []
		ret.forEach((book) => {
			if (
				book.name.toLowerCase().includes(input) ||
				book.author.toLowerCase().includes(input) ||
				book.distributor.toLowerCase().includes(input) ||
				book.category.toLowerCase().includes(input)
			) {
				results.push(book)
			}
		})
		res.send({ searchResults: results })
	} catch (error) {
		console.log(error)
		res.send({
			exitcode: 500,
		})
	}
})

export default router
