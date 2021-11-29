/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentDto } from './CommentDto';

export type ImageDto = {
    id?: number;
    userDisplayName?: string;
    gifPath?: string;
    jsonPath?: string;
    comments?: Array<CommentDto>;
    commentsSize?: number;
    commentable?: boolean;
    modifiable?: boolean;
}