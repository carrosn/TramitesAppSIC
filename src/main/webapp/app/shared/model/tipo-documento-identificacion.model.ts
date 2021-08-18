export interface ITipoDocumentoIdentificacion {
  id?: number;
  nombre?: string;
  abreviatura?: string;
}

export class TipoDocumentoIdentificacion implements ITipoDocumentoIdentificacion {
  constructor(public id?: number, public nombre?: string, public abreviatura?: string) {}
}
