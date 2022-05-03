// import accountModel from './account.schema.js'
import cloudinary from '../utils/cloudinary.js'
import mongoose from 'mongoose'
import config from '../config/config.js'
import moment from 'moment'
import orderModel from './order.model.js'
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
	star: {type:Number},
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
	getBookByCategory: async (category, id) => {
		try {
			let books = await Book.find().where('category').equals(category).limit(4).lean()
			for (let i = 0; i < books.length; i++) {
				if (String(books[i]._id) === String(id)) {
					books.splice(i, 1)
				}
			}
			return books
		} catch (error) {
			console.log(error)
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
	addBook: async (file, book) => {
		const img = await cloudinary.uploader.upload(file.path)
		const release_year = moment(book.release_year, 'DD/MM/YYYY').utc().toDate()
		console.log(book)
		const newbook = {
			name: book.name,
			author: book.author,
			distributor: book.distributor,
			seller: book.seller,
			price: book.price,
			saleprice: null,
			category: book.category,
			picture: img.secure_url,
			release_year: release_year,
			description: book.description,
			quantity: book.quantity,
			state: 1,
			star: 0,
			comments: [],
		}
		//return newbook.picture
		return await Book.create(newbook)
	},
	deleteBook: async (id) => {
		return await Book.updateOne(
			{ _id: id },
			{
				$set: {
					quantity: 0,
				},
			}
		)
	},
	updateBook: async (id, author, description, distributor, quantity, saleprice, price) => {
		await Book.updateOne(
			{ _id: id },
			{
				$set: {
					author: author,
					description: description,
					distributor: distributor,
					saleprice: saleprice == 0 ? null : saleprice,
					quantity: quantity,
					price: price,
				},
			}
		)
	},
	findBookWCategory: async (cate) => {
		return Book.find({ category: cate }).lean()
	},
	postcomment:async(email,id,star,comment,orderID)=>{
		try{
			let book = await Book.findOne({_id:id})
			const newComment = {
				userEmail:email,
				rating: star,
				review: comment,
			}
			let newStar = star
			for(let i =0;i<book.comments.length;i++){
				newStar+=book.comments[i].rating
			}
			let comments =[...book.comments,newComment]
			newStar/= comments.length*1.0
			newStar = newStar.toFixed(1)
			console.log(newStar)
			const result = await Book.findOneAndUpdate({_id:id},{star:newStar, comments:comments})
			await orderModel.updateIsReviewed(orderID,id)

			return result
		}catch(error){
			
			console.log(error)
			return null
		}
	}
		
}
