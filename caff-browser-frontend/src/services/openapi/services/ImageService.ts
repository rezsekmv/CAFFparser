/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CommentDto } from '../models/CommentDto';
import type { ImageDto } from '../models/ImageDto';
import type { PageImageDto } from '../models/PageImageDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class ImageService {

    /**
     * Összes kép megtekintése
     * @returns PageImageDto OK
     * @throws ApiError
     */
    public static getAllImage(): CancelablePromise<PageImageDto> {
        return __request({
            method: 'GET',
            path: `/api/image`,
        });
    }

    /**
     * Új kép létrehozása
     * @param formData 
     * @returns ImageDto OK
     * @throws ApiError
     */
    public static createImage(
formData?: {
image?: Blob;
},
): CancelablePromise<ImageDto> {
        return __request({
            method: 'POST',
            path: `/api/image`,
            formData: formData,
            mediaType: 'multipart/form-data',
        });
    }

    /**
     * Hozzászólás az azonosító alapján megadott képhez
     * @param requestBody 
     * @returns CommentDto OK
     * @throws ApiError
     */
    public static commentImage(
requestBody: CommentDto,
): CancelablePromise<CommentDto> {
        return __request({
            method: 'POST',
            path: `/api/image/comment`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Kép és annak részleteinek megtekintése azonosító alapján
     * @param id 
     * @returns ImageDto OK
     * @throws ApiError
     */
    public static getImage(
id: number,
): CancelablePromise<ImageDto> {
        return __request({
            method: 'GET',
            path: `/api/image/${id}`,
        });
    }

    /**
     * Saját kép eltávolítása
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static deleteMyImage(
id: number,
): CancelablePromise<any> {
        return __request({
            method: 'DELETE',
            path: `/api/image/${id}`,
        });
    }

}