export interface ITipoPersona {
  id?: number;
  codigo?: string;
  descripcion?: string;
}

export class TipoPersona implements ITipoPersona {
  constructor(public id?: number, public codigo?: string, public descripcion?: string) {}
}
