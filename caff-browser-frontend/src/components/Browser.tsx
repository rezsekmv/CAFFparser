import ImageCard from './ImageCard';
import {Pagination} from 'react-bootstrap';
import {useEffect, useState} from 'react';
import {ImageDto, ImageService} from "../services/openapi";

const IMAGES_PER_ROW = 4;
const MAX_ROWS = 2;

const Browser = () => {
    const [currentPage, setCurrentPage] = useState(0);
    const [data, setData] = useState<Array<ImageDto>>([{
            id: 1,
            comments: [{content: 'Nice Pic'}, {content: 'Great'}],
            userDisplayName: 'Jozsi',
            commentsSize: 5,
            gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp'
        }]
    );

    useEffect( () => {
        ImageService.getAllImage({}).then((page) => {
            setData(page.content!);
        }).catch((err) => {
            console.error(err?.body?.message);
        })
    }, [currentPage])

    const rows = [];
    for (let i = 0; i < data.length; i += IMAGES_PER_ROW) {
        rows.push(data.slice(i, i + IMAGES_PER_ROW).map(d =>
            <div className="col-3">
                <ImageCard id={d.id}
                           title={'Name'}
                           body={'Body'}
                           commentCount={d.commentsSize}
                           imageUrl={d.gifPath}/>
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
            <Pagination.Item onClick={() => setCurrentPage(i - 1)}
                             disabled={currentPage === i - 1}>{i}</Pagination.Item>
        );
    }

    return (
        <>
            <div className="browser">
                <h2>Image browser</h2>
                <div className="img-browser">
                    {pages[currentPage]}
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



export default Browser;