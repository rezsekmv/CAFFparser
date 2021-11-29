import { Component } from 'react';
import { useParams } from 'react-router-dom';

const ImageDetailView = () => {

    const { id } = useParams();

    return (
        <div>
            {id}
        </div>
    );
}

export default ImageDetailView;