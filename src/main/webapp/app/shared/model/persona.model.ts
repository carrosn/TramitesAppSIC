import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';
import { ITipoPersona } from 'app/shared/model/tipo-persona.model';

export interface IPersona {
  id?: number;
  numeroDocumentoIdentificacion?: string;
  nombres?: string;
  apellidos?: string;
  segundoApellido?: string;
  telefono?: string;
  direccion?: string;
  email?: string;
  tipoDocumentoIdentificacion?: ITipoDocumentoIdentificacion;
  tipoPersona?: ITipoPersona;
}

export class Persona implements IPersona {
  constructor(
    public id?: number,
    public numeroDocumentoIdentificacion?: string,
    public nombres?: string,
    public apellidos?: string,
    public segundoApellido?: string,
    public telefono?: string,
    public direccion?: string,
    public email?: string,
    public tipoDocumentoIdentificacion?: ITipoDocumentoIdentificacion,
    public tipoPersona?: ITipoPersona
  ) {}
}
