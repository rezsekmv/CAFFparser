/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */

import type { CommentDto } from './CommentDto';

export type ImageDto = {
    id?: number;
    userDisplayName?: string;
    gifPath?: string;
    caffPath?: string;
    comments?: Array<CommentDto>;
    commentsSize?: number;
    commentable?: boolean;
    modifiable?: boolean;
    date?: string;
    credit?: string;
    captions?: Array<string>;
    tags?: Array<string>;
    height?: number;
    width?: number;
}