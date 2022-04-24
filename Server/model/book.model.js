// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const BookSchema = new mongoose.Schema({
    id: { type: Number, required: true, min: 0, unique: true },
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
    comments: [{
        userEmail: { type: String },
        rating: { type: Number },
        review: { type: String }
    }, ]
})

const Book = mongoose.model('book', BookSchema, 'book')

export default {

    getBookOnSale: async() => {
        try {
            const books = await Book.find({ saleprice: { $ne: null } }).lean()
            return books.splice(0, 5)
        } catch (err) {
            console.log(err)
            return null
        }
    },
    updateQuantity:async(bookID,quantity)=>{
        try {
            const books = await Book.findOneAndUpdate({id:bookID},{quantity:quantity}).lean()
        } catch (err) {
            console.log(err)
            return null
        }
    },
    getBook: async(bookID) => {
        const book= await Book.findOne({ _id: bookID })
        console.log(book)
        return book
    },
    findAll: async() => {
        return await Book.find({}).lean() || null;
    },
    
}