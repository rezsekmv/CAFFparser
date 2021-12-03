import ImageCard from './ImageCard';
import FileUpload from './FileUpload';
import { Pagination } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { ImageDto, ImageService } from '../services/openapi';

const Browser = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [lastPage, setLastPage] = useState(5);
  const [data, setData] = useState<Array<ImageDto>>([]);

  useEffect(() => {
    ImageService.getAllImage(8, currentPage)
      .then((page) => {
        setData(page.content!);
        setLastPage(page.totalPages!);
      })
      .catch((err) => {
        console.error(JSON.stringify(err));
      });
  }, [currentPage]);

  return (
    <>
      <div className="browser">
        <h2 className="float-start">Image browser</h2>
        <div className="row">
          <div className="col-5">
            <FileUpload></FileUpload>
          </div>
        </div>
        <div className="img-browser">
          <div className="row img-browser">
            {data.map((gif: ImageDto, idx) => {
              return (
                <div key={idx} className="col-3 mb-5">
                  <ImageCard
                    id={gif.id}
                    title={gif.caption}
                    user={gif.credit}
                    commentCount={gif.commentsSize}
                    gifUrl={gif.gifPath}
                    caffUrl={gif.caffPath}
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
