export interface IUsuarios {
  id?: number;
  name?: string;
  email?: string;
  gender?: string;
  status?: string;
}

export class Usuarios implements IUsuarios {
  constructor(public id?: number, public name?: string, public email?: string, public gender?: string, public status?: string) {}
}
