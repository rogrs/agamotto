import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITurnos, defaultValue } from 'app/shared/model/turnos.model';

export const ACTION_TYPES = {
  SEARCH_TURNOS: 'turnos/SEARCH_TURNOS',
  FETCH_TURNOS_LIST: 'turnos/FETCH_TURNOS_LIST',
  FETCH_TURNOS: 'turnos/FETCH_TURNOS',
  CREATE_TURNOS: 'turnos/CREATE_TURNOS',
  UPDATE_TURNOS: 'turnos/UPDATE_TURNOS',
  DELETE_TURNOS: 'turnos/DELETE_TURNOS',
  RESET: 'turnos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITurnos>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type TurnosState = Readonly<typeof initialState>;

// Reducer

export default (state: TurnosState = initialState, action): TurnosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_TURNOS):
    case REQUEST(ACTION_TYPES.FETCH_TURNOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TURNOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TURNOS):
    case REQUEST(ACTION_TYPES.UPDATE_TURNOS):
    case REQUEST(ACTION_TYPES.DELETE_TURNOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_TURNOS):
    case FAILURE(ACTION_TYPES.FETCH_TURNOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TURNOS):
    case FAILURE(ACTION_TYPES.CREATE_TURNOS):
    case FAILURE(ACTION_TYPES.UPDATE_TURNOS):
    case FAILURE(ACTION_TYPES.DELETE_TURNOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_TURNOS):
    case SUCCESS(ACTION_TYPES.FETCH_TURNOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_TURNOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TURNOS):
    case SUCCESS(ACTION_TYPES.UPDATE_TURNOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TURNOS):
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

const apiUrl = 'api/turnos';
const apiSearchUrl = 'api/_search/turnos';

// Actions

export const getSearchEntities: ICrudSearchAction<ITurnos> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_TURNOS,
  payload: axios.get<ITurnos>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<ITurnos> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TURNOS_LIST,
  payload: axios.get<ITurnos>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ITurnos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TURNOS,
    payload: axios.get<ITurnos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITurnos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TURNOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITurnos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TURNOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITurnos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TURNOS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
