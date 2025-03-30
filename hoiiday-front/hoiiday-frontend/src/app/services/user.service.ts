import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../model/user.model';

@Injectable({
  providedIn: 'root',
})

export class UserService {
  private baseUrl = 'http://localhost:8080/api/users'; 

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<User[]>{
    let my_url = this.baseUrl;
    return this.http.get<User[]>(my_url);
  }

  deleteUser(id: number): Observable<any>{
    const url = `${this.baseUrl}/${id}`;
    return this.http.delete(url);
  }

//   updateUser(user: User): Observable<User> {
//     const url = `${this.baseUrl}/${user.id}`;
//     return this.http.put<User>(url, user);
// }
}
