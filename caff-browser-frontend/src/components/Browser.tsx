import ImageCard from './ImageCard';
import { Pagination } from 'react-bootstrap';
import { Component } from 'react';

const IMAGES_PER_ROW = 4;
const MAX_ROWS = 2;

class Browser extends Component {

    constructor(props: any) {
        super(props);
        this.state = {
            currentPage: 0
        };
    }

    render() {
        const data = [{
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },
        {
            id: 1,
            title: 'Sus animation',
            body: 'Yes, it is a very suspicious anim gif',
            commentCount: 5,
            imageUrl: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        },];

        const rows = [];
        for (let i = 0; i < data.length; i += IMAGES_PER_ROW) {
            rows.push(data.slice(i, i + IMAGES_PER_ROW).map(d =>
                <div className="col-3">
                    <ImageCard id={d.id}
                        title={d.title}
                        body={d.body}
                        commentCount={d.commentCount}
                        imageUrl={d.imageUrl}></ImageCard>
                </div>
            ));
        }
        const pages = [];
        for (let i = 0; i < rows.length; i += MAX_ROWS) {
            pages.push(rows.slice(i, i + MAX_ROWS).map(row =>
                <div className="row img-browser">
                    {row}
                </div>
            ));
        }

        const paginationItems = [];
        for (let i = 1; i <= pages.length; i++) {
            paginationItems.push(
                <Pagination.Item onClick={() => this.setState({ currentPage: i - 1 })} disabled={this.state.currentPage === i - 1}>{i}</Pagination.Item>
            );
        }

        return (
            <>
                <div className="browser">
                    <h2>Image browser</h2>
                    <div className="img-browser">
                        {pages[this.state.currentPage]}
                    </div>
                </div>
                <div className="browser-pager">
                    <Pagination>
                        {paginationItems}
                    </Pagination>
                </div>
            </>
        );
    }
}

export default Browser;