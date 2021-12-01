import { faSmileBeam } from '@fortawesome/free-regular-svg-icons';
import { Component, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { ImageDto, CommentDto, ImageService } from '../services/openapi';
import StaticService from '../services/StaticService';
import CommentCard from './CommentCard';

const ImageDetailView = () => {
  const [image, setImage] = useState<ImageDto>();
  const { id } = useParams();

  useEffect(() => {
    ImageService.getImage(+id!)
      .then((img) => {
        setImage(img);
      })
      .catch((error) => console.error('Failed to fetch image!'));
      
  }, [id]);

  return (
    <div>
      <img
        style={styles.mainImage}
        src={StaticService.getImage(image?.gifPath!)}
      />
      <div style={styles.infoBar} className='mx-auto my-3'>
        <h2>{image?.userDisplayName}</h2>
      </div>
      <div style={styles.commentsSection}>
        {image?.comments?.map((comment) => (
          <CommentCard
            content={comment.content!}
            id={comment.id!}
            modifiable={comment.modifiable!}
            username={comment.userDisplayName!}
            imageId={comment.imageId!}
          />
        ))}
      </div>
    </div>
  );
};

const styles = {
  mainImage: {
    display: 'block',
    margin: 'auto',
    'max-width': 400,
    'max-height': 400,
  },
  infoBar: {
    maxWidth: 400,
    height: 40,
  },
  commentsSection: {
    width: '400px',
    display: 'block',
    margin: 'auto',
  },
};

export default ImageDetailView;
