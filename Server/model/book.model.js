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
    state: Boolean,
    star: Number,
    comments:[{
        userEmail: {type: String},
        rating: {type: Number},
        review: {type: String}
    },]
})

const Book = mongoose.model('book', BookSchema, 'book')

export default {
    getBook: async (bookID) => {
        return await Book.findOne({_id:bookID})
    },
    findAll: async()=>{
        return await Book.find({}).lean()||null;
    }
}