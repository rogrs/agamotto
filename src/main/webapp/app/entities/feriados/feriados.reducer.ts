import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFeriados, defaultValue } from 'app/shared/model/feriados.model';

export const ACTION_TYPES = {
  SEARCH_FERIADOS: 'feriados/SEARCH_FERIADOS',
  FETCH_FERIADOS_LIST: 'feriados/FETCH_FERIADOS_LIST',
  FETCH_FERIADOS: 'feriados/FETCH_FERIADOS',
  CREATE_FERIADOS: 'feriados/CREATE_FERIADOS',
  UPDATE_FERIADOS: 'feriados/UPDATE_FERIADOS',
  DELETE_FERIADOS: 'feriados/DELETE_FERIADOS',
  RESET: 'feriados/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFeriados>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type FeriadosState = Readonly<typeof initialState>;

// Reducer

export default (state: FeriadosState = initialState, action): FeriadosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_FERIADOS):
    case REQUEST(ACTION_TYPES.FETCH_FERIADOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FERIADOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FERIADOS):
    case REQUEST(ACTION_TYPES.UPDATE_FERIADOS):
    case REQUEST(ACTION_TYPES.DELETE_FERIADOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_FERIADOS):
    case FAILURE(ACTION_TYPES.FETCH_FERIADOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FERIADOS):
    case FAILURE(ACTION_TYPES.CREATE_FERIADOS):
    case FAILURE(ACTION_TYPES.UPDATE_FERIADOS):
    case FAILURE(ACTION_TYPES.DELETE_FERIADOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_FERIADOS):
    case SUCCESS(ACTION_TYPES.FETCH_FERIADOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FERIADOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FERIADOS):
    case SUCCESS(ACTION_TYPES.UPDATE_FERIADOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FERIADOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/feriados';
const apiSearchUrl = 'api/_search/feriados';

// Actions

export const getSearchEntities: ICrudSearchAction<IFeriados> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_FERIADOS,
  payload: axios.get<IFeriados>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IFeriados> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_FERIADOS_LIST,
  payload: axios.get<IFeriados>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IFeriados> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FERIADOS,
    payload: axios.get<IFeriados>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFeriados> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FERIADOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFeriados> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FERIADOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFeriados> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FERIADOS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
