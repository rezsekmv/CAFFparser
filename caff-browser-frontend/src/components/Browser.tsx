import ImageCard from './ImageCard';
import { Pagination } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { ImageDto, ImageService } from '../services/openapi';
import StaticService from '../services/StaticService'

const Browser = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [lastPage, setLastPage] = useState(5);
  const [data, setData] = useState<Array<ImageDto>>([
    {
      id: 1,
      comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
      userDisplayName: 'Jozsi',
      commentsSize: 2,
      gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
    },
    {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      },
      {
        id: 1,
        comments: [{ content: 'Nice Pic' }, { content: 'Great' }],
        userDisplayName: 'Jozsi',
        commentsSize: 2,
        gifPath: 'https://i.giphy.com/media/j4fbBhYgu8mNEHkQ4w/giphy.webp',
      } 
  ]);

  useEffect(() => {
    ImageService.getAllImage({pageSize: 8, pageNumber: currentPage})
      .then((page) => {
        setData(page.content!);
        setLastPage(page.totalPages!);
      })
      .catch((err) => {
        console.error(err?.body?.message);
      });
  }, [currentPage]);

  return (
    <>
      <div className="browser">
        <h2>Image browser</h2>
        <div className="img-browser">
          <div className="row img-browser">
            {data.map((gif: ImageDto, idx) => {
              return (
                <div key={idx} className="col-3 mb-5">
                  <ImageCard
                    id={gif.id}
                    title={'Caption'}
                    body={'Body'}
                    commentCount={gif.commentsSize}
                    imageUrl={StaticService.getImage(gif.gifPath!)}
                  />
                </div>
              );
            })}
          </div>
        </div>
      </div>
      <div className="browser-pager">
        <Pagination>
          {[...Array(lastPage)].map((e, idx) => {
            return (
              <Pagination.Item
                key={idx}
                onClick={() => setCurrentPage(idx)}
                disabled={currentPage === idx}
              >
                {idx + 1}
              </Pagination.Item>
            );
          })}
        </Pagination>
      </div>
    </>
  );
};

export default Browser;
