import { Role } from "./role.enum";

export class User {
    constructor(
        public userId:number,
        public firstName: string,
        public lastName: string,
        public email: string,
        public phoneNumber: string,
        public role: Role,
        public password?:string
    ){}
}

