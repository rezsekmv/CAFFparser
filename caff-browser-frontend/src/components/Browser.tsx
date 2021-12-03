import ImageCard from './ImageCard';
import FileUpload from './FileUpload';
import { Pagination, FormControl } from 'react-bootstrap';
import { useEffect, useState } from 'react';
import { ImageDto, ImageService } from '../services/openapi';

const Browser = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [lastPage, setLastPage] = useState(5);
  const [data, setData] = useState<Array<ImageDto>>([]);
  const [caption, setCaption] = useState('');
  const [credit, setCredit] = useState('');

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

  const handleCaptionFilter = (e: any) => {
    const c = e.target.value;
    setCaption(c);
    ImageService.getAllImage(8, currentPage, credit, c)
      .then((page) => {
        setData(page.content!);
        setLastPage(page.totalPages!);
      })
      .catch((err) => {
        console.error(JSON.stringify(err));
      });
  };

  const handleCreditFilter = (e: any) => {
    const c = e.target.value;
    setCredit(c);
    ImageService.getAllImage(8, currentPage, c, caption)
      .then((page) => {
        setData(page.content!);
        setLastPage(page.totalPages!);
      })
      .catch((err) => {
        console.error(JSON.stringify(err));
      });
  };

  return (
    <>
      <div className="browser">
        <h2 className="float-start">Image browser</h2>
        <div className="row mt-2 float-end">
          <FileUpload></FileUpload>
        </div>
        <div className="row p-2">
          <div className="col-6">
            <FormControl
              type="text"
              placeholder="caption"
              value={caption}
              onChange={(e: any) => {
                handleCaptionFilter(e);
              }}
            ></FormControl>
          </div>
          <div className="col-6">
            <FormControl
              type="text"
              placeholder="credit"
              value={credit}
              onChange={(e: any) => handleCreditFilter(e)}
            ></FormControl>
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
