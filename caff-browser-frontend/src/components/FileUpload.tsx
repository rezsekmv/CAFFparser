import { useState } from 'react';
import { Button, Form } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { ImageService } from '../services/openapi';

const FileUpload = () => {
  const [selectedFile, setSelectedFile] = useState<any>();
  const navigate = useNavigate();

  const handleCreateCaff = () => {
    ImageService.createImage({ image: selectedFile }).then((res) => {
      navigate(`/image/${res.id}`);
    });
  };

  const handleFileUpload = (e: any) => {
    setSelectedFile(e.target.files[0]);
  };

  return (
    <>
      <Form.Control
        placeholder={'Caff file'}
        onChange={(e) => {
          handleFileUpload(e);
        }}
        type="file"
        name="file"
      />
      <Button onClick={() => handleCreateCaff()}>Upload</Button>
    </>
  );
};

export default FileUpload;
