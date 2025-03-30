import { Injectable } from "@angular/core";
import { UserService } from "./user.service";
import { map, Observable } from "rxjs";
import { User } from "../model/user.model";

@Injectable({
    providedIn: 'root',
})
export class ClientService{
    constructor(private userService: UserService){}
    
    getClients(): Observable<User[]> {
    return this.userService.getAllUsers().pipe(
      map((users) => users.filter((user) => user.role === 'CLIENT'))
    );
    }

    deleteClient(userId: number): Observable<any>{
        return this.userService.deleteUser(userId);
    }

    // updateClient(user: User): Observable<User>{
    //     return this.userService.updateUser(user);
    // }
}

