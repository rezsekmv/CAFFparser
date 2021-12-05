import { useState } from 'react';
import { Button, FormControl } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { ImageService } from '../services/openapi';

interface FileUploadProps {
  loading: Function;
}

const FileUpload = ({ loading }: FileUploadProps) => {
  const [selectedFile, setSelectedFile] = useState<any>();
  const navigate = useNavigate();

  const handleCreateCaff = () => {
    loading(true);
    ImageService.createImage({ image: selectedFile }).then((res) => {
      loading(false);
      navigate(`/image/${res.id}`);
    });
  };

  const handleFileUpload = (e: any) => {
    setSelectedFile(e.target.files[0]);
  };

  return (
    <>
      <div className="col">
        <FormControl
          className="col"
          placeholder={'Caff file'}
          onChange={(e) => {
            handleFileUpload(e);
          }}
          type="file"
          name="file"
        />
      </div>
      <div className="col">
        <Button onClick={() => handleCreateCaff()}>Upload</Button>
      </div>
    </>
  );
};
export default FileUpload;
