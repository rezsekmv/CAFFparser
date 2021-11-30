/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CommentDto } from '../models/CommentDto';
import type { CancelablePromise } from '../core/CancelablePromise';
import { request as __request } from '../core/request';

export class CommentService {

    /**
     * Hozzászólás módosítása
     * @param id 
     * @param requestBody 
     * @returns CommentDto OK
     * @throws ApiError
     */
    public static updateComment(
id: number,
requestBody: CommentDto,
): CancelablePromise<CommentDto> {
        return __request({
            method: 'PUT',
            path: `/api/comment/${id}`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

    /**
     * Hozzászólás eltávolítása
     * @param id 
     * @returns any OK
     * @throws ApiError
     */
    public static deleteComment(
id: number,
): CancelablePromise<any> {
        return __request({
            method: 'DELETE',
            path: `/api/comment/${id}`,
        });
    }

    /**
     * Hozzászólás az azonosító alapján megadott képhez
     * @param requestBody 
     * @returns CommentDto OK
     * @throws ApiError
     */
    public static createComment(
requestBody: CommentDto,
): CancelablePromise<CommentDto> {
        return __request({
            method: 'POST',
            path: `/api/comment`,
            body: requestBody,
            mediaType: 'application/json',
        });
    }

}