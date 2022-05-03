import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

let dump = ''

function stringToASCII(str) {
    try {
        return str
            .replace(/[àáảãạâầấẩẫậăằắẳẵặ]/g, 'a')
            .replace(/[èéẻẽẹêềếểễệ]/g, 'e')
            .replace(/[đ]/g, 'd')
            .replace(/[ìíỉĩị]/g, 'i')
            .replace(/[òóỏõọôồốổỗộơờớởỡợ]/g, 'o')
            .replace(/[ùúủũụưừứửữự]/g, 'u')
            .replace(/[ỳýỷỹỵ]/g, 'y')
    } catch {
        return str
    }
}

router.post('/filter', async(req, res) => {
    try {
        console.log(dump)
        const ret = await bookModel.findAll()
        let { category, max, min } = req.body

        if (min == '') min = 0
        if (max == '') max = Number.MAX_SAFE_INTEGER

        let temp = [],
            temp1 = [],
            results = []
        ret.forEach((book) => {
            if (
                stringToASCII(book.name.toLowerCase()).includes(stringToASCII(dump)) ||
                stringToASCII(book.author.toLowerCase()).includes(stringToASCII(dump)) ||
                stringToASCII(book.distributor.toLowerCase()).includes(stringToASCII(dump)) ||
                stringToASCII(book.category.toLowerCase()).includes(stringToASCII(dump))
            ) {
                temp.push(book)
            }
        })
        temp.forEach((book) => {
            if (book.saleprice != null) {
                if (+book.saleprice >= +min && +book.saleprice <= +max) temp1.push(book)
            } else {
                if (+book.price >= +min && +book.price <= +max) temp1.push(book)
            }
        })
        if (category.length > 0) {
            category.forEach((cate) => {
                temp1.forEach((book) => {
                    if (book.category == cate) results.push(book)
                })
            })
        } else {
            temp1.forEach((book) => {
                results.push(book)
            })
        }

        res.send({ filterResults: results })
    } catch (error) {
        res.send({
            exitcode: 500,
        })
    }
})

router.post('/', async(req, res) => {
    try {
        const ret = await bookModel.findAll()
        const input = req.body.input.toLowerCase()
        dump = input
        let results = []
        ret.forEach((book) => {
            if (
                stringToASCII(book.name.toLowerCase()).includes(stringToASCII(input)) ||
                stringToASCII(book.author.toLowerCase()).includes(stringToASCII(input)) ||
                stringToASCII(book.distributor.toLowerCase()).includes(stringToASCII(input)) ||
                stringToASCII(book.category.toLowerCase()).includes(stringToASCII(input))
            ) {
                results.push(book)
            }
        })
        res.send({ searchResults: results })
    } catch (error) {
        res.send({
            exitcode: 500,
        })
    }
})

export default router