import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

export interface IEncuesta {
  id?: number;
  preguntaOne?: string;
  niveSatisfacion?: string;
  nivelSatisfacion?: INivelSatisfacion;
}

export class Encuesta implements IEncuesta {
  constructor(
    public id?: number,
    public preguntaOne?: string,
    public niveSatisfacion?: string,
    public nivelSatisfacion?: INivelSatisfacion
  ) {}
}
