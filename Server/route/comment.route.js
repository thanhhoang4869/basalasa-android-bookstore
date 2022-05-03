import bookModel from '../model/book.model.js'
import bodyParser from 'body-parser'
import express from 'express'

const router = express.Router()
router.use(bodyParser.urlencoded({ extended: false }))

router.post('/',async(req,res)=>{
	try{
		const data={
            email:req.payload.email
        }
		console.log("email")

		const _id = req.body._id
		const star = req.body.rating
		const comment = req.body.review
		const orderID = req.body.orderID

		console.log(data.email)
		let response = await bookModel.postcomment(data.email,_id,star,comment,orderID)
		if(!response){
			res.send({
				exitcode:500
			})
		}
		else{
           
			res.send({
				exitcode:0
			})
		}
		
	}catch(error){
		console.log(error)
		res.send({
			exitcode:500
		})
	}

})



export default router
