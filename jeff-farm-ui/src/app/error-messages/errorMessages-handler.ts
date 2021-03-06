import { ErrorHandler, Injectable } from '@angular/core';

import { ErrorMessagesService } from './error-messages.service';

@Injectable()
export class ErrorMessagesHandler implements ErrorHandler {

    constructor(private errorMessagesService: ErrorMessagesService) { }

    handleError(error: Error): void {
        if (this.errorMessagesService != null) {
            this.errorMessagesService.add(error.message);
        }
        throw error;
    }
}
