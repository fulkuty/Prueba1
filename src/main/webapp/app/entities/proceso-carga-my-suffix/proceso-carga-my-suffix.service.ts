import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ProcesoCargaMySuffix } from './proceso-carga-my-suffix.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ProcesoCargaMySuffix>;

@Injectable()
export class ProcesoCargaMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/proceso-cargas';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(procesoCarga: ProcesoCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(procesoCarga);
        return this.http.post<ProcesoCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(procesoCarga: ProcesoCargaMySuffix): Observable<EntityResponseType> {
        const copy = this.convert(procesoCarga);
        return this.http.put<ProcesoCargaMySuffix>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ProcesoCargaMySuffix>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ProcesoCargaMySuffix[]>> {
        const options = createRequestOption(req);
        return this.http.get<ProcesoCargaMySuffix[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ProcesoCargaMySuffix[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ProcesoCargaMySuffix = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ProcesoCargaMySuffix[]>): HttpResponse<ProcesoCargaMySuffix[]> {
        const jsonResponse: ProcesoCargaMySuffix[] = res.body;
        const body: ProcesoCargaMySuffix[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ProcesoCargaMySuffix.
     */
    private convertItemFromServer(procesoCarga: ProcesoCargaMySuffix): ProcesoCargaMySuffix {
        const copy: ProcesoCargaMySuffix = Object.assign({}, procesoCarga);
        copy.programado = this.dateUtils
            .convertDateTimeFromServer(procesoCarga.programado);
        copy.inicio = this.dateUtils
            .convertDateTimeFromServer(procesoCarga.inicio);
        copy.fin = this.dateUtils
            .convertDateTimeFromServer(procesoCarga.fin);
        return copy;
    }

    /**
     * Convert a ProcesoCargaMySuffix to a JSON which can be sent to the server.
     */
    private convert(procesoCarga: ProcesoCargaMySuffix): ProcesoCargaMySuffix {
        const copy: ProcesoCargaMySuffix = Object.assign({}, procesoCarga);

        copy.programado = this.dateUtils.toDate(procesoCarga.programado);

        copy.inicio = this.dateUtils.toDate(procesoCarga.inicio);

        copy.fin = this.dateUtils.toDate(procesoCarga.fin);
        return copy;
    }
}
