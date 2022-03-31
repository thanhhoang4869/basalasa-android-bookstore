// import accountModel from './account.schema.js'

import mongoose from 'mongoose'
import config from '../config/config.js'
const url = config.url

mongoose.connect(url, {
    useNewUrlParser: true,
    useUnifiedTopology: true,
})

const CategorySchema = new mongoose.Schema({
    name: { type: String, required: true },
    image: { type: String },
})
const Category = mongoose.model('category', CategorySchema, 'category')

export default {
    async getCategory() {
        const ret = await Category.find({}).lean();
        return ret || null
    }
}