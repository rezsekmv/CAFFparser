import ImageCard from './ImageCard';
import { Pagination } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { PageImageDto } from '../services/openapi/models/PageImageDto';
import { ImageDto, ImageService } from '../services/openapi';

const IMAGES_PER_ROW = 4;
const MAX_ROWS = 2;

const Browser = () => {

    const [numberOfPages, setNumberOfPages] = useState(0);
    const [currentPage, setCurrentPage] = useState(0);
    const [data, setData] = useState<Array<ImageDto>>([]);

    useEffect(() => {
        setData(Array(8 - currentPage).fill({
            commentable: true,
            comments: [{
                content: 'Ez egy komment',
                id: 1,
                imageId: 1,
                modifiable: true,
                userDisplayName: 'User'
            }],
            commentsSize: 10,
            gifPath: 'https://i0.wp.com/www.printmag.com/wp-content/uploads/2021/02/4cbe8d_f1ed2800a49649848102c68fc5a66e53mv2.gif?fit=476%2C280&ssl=1',
            id: 1,
            jsonPath: '',
            modifiable: true,
            userDisplayName: 'User'
        }));
        setNumberOfPages(2);
    });

    const rows = [];
    for (let i = 0; i < data.length; i += IMAGES_PER_ROW) {
        rows.push(data.slice(i, i + IMAGES_PER_ROW).map(d =>
            <div className="col-3">
                <ImageCard id={d.id}
                    commentCount={d.commentsSize}
                    imageUrl={d.gifPath}
                    userName={d.userDisplayName}></ImageCard>
            </div>
        ));
    }
    const page = rows.slice(0, MAX_ROWS).map(row =>
        <div className="row img-browser">
            {row}
        </div>
    );

    const paginationItems = [];
    for (let i = 1; i <= 2; i++) {
        paginationItems.push(
            <Pagination.Item onClick={() => setCurrentPage(i - 1)} disabled={currentPage === i - 1}>{i}</Pagination.Item>
        );
    }

    return (
        <>
            <div className="browser">
                <h2>Image browser</h2>
                <div className="img-browser">
                    {page}
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