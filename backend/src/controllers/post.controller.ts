import * as express from "express"
import Post from "../models/post"
import User from "../models/user"


export class PostController {
    new_post = async (req: express.Request, res: express.Response) => {
        const post = new Post({
            post_type: req.body.post_type,
            creator_type: req.body.creator_type,
            creator: req.body.creator,
            post_text: req.body.post_text,
            post_image: req.body.post_image,
            job_title: req.body.job_title,
            page_name: req.body.page_name,
            date_posted: Date.now()
        });

        post.save().then(post => {
            res.json('success')
        })
    }

    get_user_posts = async (req: express.Request, res: express.Response) => {
        const user = req.params.user

        let posts = await Post.find({
            creator: user,
            creator_type: 0
        })

        res.json(posts)
    }

    get_page_posts = async (req: express.Request, res: express.Response) => {
        const page = req.params.page

        let posts = await Post.find({
            creator: page,
            creator_type: 1
        })

        res.json(posts)
    }

    get_posts = async (req: express.Request, res: express.Response) => {
        const email = req.params.email
        const timestamp = req.query.timestamp
        const page_size = 2
        const page = parseInt(req.query.page.toString()) - 1

        let user = await User.findOne({
            email: email
        })

        let connections = user.connections.map(conn => conn.user)
        let followed_pages = user.followed_pages.map(page => page.name)
        let second_conn = []

        connections.forEach(async conn => {
            let user = await User.findOne({
                email: conn
            })

            let c = user.connections.map(conn => conn.user)
            c.filter(conn => conn != email)
            second_conn = second_conn.concat(c)
        });

        connections = connections.concat(second_conn)

        let posts = await Post.find({
            $or: [ {creator_type: 0, creator: {$in: connections}}, {creator_type: 1, creator: {$in: followed_pages}}],
            $lt: {date_posted: timestamp}
        })

        posts = posts.reverse()
        posts = posts.slice(page * page_size, (page + 1) * page_size)
        res.json(posts)
    }

    get_post_by_id = async (req: express.Request, res: express.Response) => {
        const post_id = req.params.post_id

        let post = await Post.findOne({
            _id: post_id
        })

        res.json(post)
    }

    add_comment = async (req: express.Request, res: express.Response) => {
        const user = req.body.user
        const profile_image = req.body.profile_image
        const post_id = req.body.post_id
        const text = req.body.text

        await Post.findOneAndUpdate({
            _id: post_id
        }, {
            $push: {comments: {user: user, profile_image: profile_image, text: text}}
        })

        res.json('success')
    }
}