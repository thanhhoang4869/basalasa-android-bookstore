// import accountModel from './account.schema.js'
import cloudinary from '../utils/cloudinary.js'
import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
	useNewUrlParser: true,
	useUnifiedTopology: true,
})

const BookSchema = new mongoose.Schema({
	name: { type: String, required: true },
	author: { type: String, required: true },
	distributor: { type: String, required: true },
	seller: { type: String, required: true },
	price: { type: Number, required: true },
	saleprice: Number,
	category: { type: String, required: true },
	picture: { type: String, require: true },
	release_year: Date,
	description: { type: String, required: true },
	quantity: { type: Number, required: true, min: 1 },
	state: Number,
	star: Number,
	comments: [
		{
			userEmail: { type: String },
			rating: { type: Number },
			review: { type: String },
		},
	],
})

const Book = mongoose.model('book', BookSchema, 'book')

export default {
	getBookOnSale: async () => {
		try {
			const books = await Book.find({ saleprice: { $ne: null } }).lean()
			return books.splice(0, 5)
		} catch (err) {
			console.log(err)
			return null
		}
	},
	updateQuantity: async (bookID, quantity) => {
		try {
			const books = await Book.findOneAndUpdate({ id: bookID }, { quantity: quantity }).lean()
		} catch (err) {
			console.log(err)
			return null
		}
	},
	getBook: async (bookID) => {
		return await Book.findOne({ _id: bookID })
	},
	findAll: async () => {
		return (await Book.find({}).lean()) || null
	},
	findBookWSeller: async (seller, id) => {
		return await Book.findOne({ seller: seller, _id: id })
	},
	addBook:async(file,book)=>{
		const img = await cloudinary.uploader.upload(file.path)	
		const newbook = {
			name: book.name,
			author: book.author,
			distributor: book.distributor,
			seller: book.seller,
			price: book.price,
			saleprice: book.saleprice?book.saleprice:null,
			category: book.category,
			picture: img.secure_url,
			release_year: new Date(),
			description: book.description,
			quantity: book.quantity,
			state: 1,
			star: 0,
			comments: [ 
			],
		  }
		//return newbook.picture
		return await Book.create(newbook)
	}
}
