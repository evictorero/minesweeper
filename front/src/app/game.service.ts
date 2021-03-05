import { environment } from '../environments/environment';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { Game, PlayMoveDTO, StartGameDTO } from './entities';
import { catchError } from 'rxjs/operators';
import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  protected baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) {}

  getAll (): Observable<Array<Game>> {
    // @ts-ignore
    return this.http.get<Array<Game>>(this.baseUrl + '/games', {observe: 'response'})
      .map(response => {
          return response.body;
        },
      )
      .pipe(
        catchError(err => { return this.handleError(err); }));
  }

  play(gameId: number, playMoveDTO: PlayMoveDTO): Observable<Game> {
    const body = JSON.stringify(playMoveDTO);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.patch<Game>(this.baseUrl + '/games/' + gameId + '/play', body, {headers}).pipe(catchError(this.handleError));
  }

  create(startGameDTO: StartGameDTO ): Observable<Game> {
    const body = JSON.stringify(startGameDTO);
    const headers = new HttpHeaders({'Content-Type': 'application/json'});
    return this.http.post<Game>(this.baseUrl + '/games', body, {headers}).pipe(catchError(this.handleError));
  }

  handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.error.code}, ` +
        `body was: ${error.error.message}`);
    }
    // return an ErrorObservable with a user-facing error message
    return throwError (
      error.error.message);
  }
}
