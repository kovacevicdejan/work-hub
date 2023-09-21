import * as express from "express"
import Page from "../models/page"
import User from "../models/user"

export class PageController {
    create_page = async (req: express.Request, res: express.Response) => {
        const page = new Page({
            name: req.body.name,
            headline: req.body.headline,
            about: req.body.about,
            profile_image: req.body.profile_image,
            specialties: req.body.specialties,
            website: req.body.website,
            location: req.body.location,
            admin: req.body.admin,
            date_created: Date.now()
        });

        page.save().then(page => {
            res.json('success')
        })
    }

    get_page_by_name = async (req: express.Request, res: express.Response) => {
        const name = req.params.name;

        const page = await Page.findOne({
            'name': name
        })

        res.json(page)
    }

    get_recommended_pages = async (req: express.Request, res: express.Response) => {
        const email = req.params.email;
        const user = await User.findOne({email: email})
        const followed_pages = user.followed_pages.map(page => page.name)

        Page.find({
            name: {$nin: followed_pages}
        }, (err, pages) => {
            res.json(pages);
        })
    }

    follow = async (req: express.Request, res: express.Response) => {
        const user = req.body.user
        const page = req.body.page

        await User.findOneAndUpdate({
            email: user
        }, {
            $push: {followed_pages: {name: page}}
        })

        await Page.findOneAndUpdate({
            name: page
        }, {
            $push: {followers: {user: user}}
        })

        res.json("success")
    }

    unfollow = async (req: express.Request, res: express.Response) => {
        const user = req.body.user
        const page = req.body.page

        await User.findOneAndUpdate({
            email: user
        }, {
            $pull: {followed_pages: {name: page}}
        })

        await Page.findOneAndUpdate({
            name: page
        }, {
            $pull: {followers: {user: user}}
        })

        res.json("success")
    }

    send_review = async (req: express.Request, res: express.Response) => {
        const user = req.body.user
        const page = req.body.page
        const text = req.body.text
        const user_image = req.body.user_image

        await Page.findOneAndUpdate({
            name: page
        }, {
            $push: {reviews: {user: user, text: text, user_image: user_image}}
        })

        res.json("success")
    }

    search = async (req: express.Request, res: express.Response) => {
        const keyword = req.params.keyword

        const users = await Page.find({
            name: { $regex: keyword, $options: 'i' }
        })

        res.json(users)
    }
}