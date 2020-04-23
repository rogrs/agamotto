import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { getEntities as getUnidadeNegocios } from 'app/entities/unidade-negocios/unidade-negocios.reducer';
import { getEntity, updateEntity, createEntity, reset } from './feriados.reducer';
import { IFeriados } from 'app/shared/model/feriados.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFeriadosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const FeriadosUpdate = (props: IFeriadosUpdateProps) => {
  const [unidadeNegociosId, setUnidadeNegociosId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { feriadosEntity, unidadeNegocios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/feriados');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUnidadeNegocios();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...feriadosEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="agamottoApp.feriados.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.feriados.home.createOrEditLabel">Create or edit a Feriados</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : feriadosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="feriados-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="feriados-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="feriados-nome">
                  <Translate contentKey="agamottoApp.feriados.nome">Nome</Translate>
                </Label>
                <AvField
                  id="feriados-nome"
                  type="text"
                  name="nome"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="fixoOuMudaTodoAnoLabel">
                  <AvInput id="feriados-fixoOuMudaTodoAno" type="checkbox" className="form-check-input" name="fixoOuMudaTodoAno" />
                  <Translate contentKey="agamottoApp.feriados.fixoOuMudaTodoAno">Fixo Ou Muda Todo Ano</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="dataFeriadoLabel" for="feriados-dataFeriado">
                  <Translate contentKey="agamottoApp.feriados.dataFeriado">Data Feriado</Translate>
                </Label>
                <AvField
                  id="feriados-dataFeriado"
                  type="date"
                  className="form-control"
                  name="dataFeriado"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="feriados-unidadeNegocios">
                  <Translate contentKey="agamottoApp.feriados.unidadeNegocios">Unidade Negocios</Translate>
                </Label>
                <AvInput id="feriados-unidadeNegocios" type="select" className="form-control" name="unidadeNegocios.id">
                  <option value="" key="0" />
                  {unidadeNegocios
                    ? unidadeNegocios.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.razaoSocial}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/feriados" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  unidadeNegocios: storeState.unidadeNegocios.entities,
  feriadosEntity: storeState.feriados.entity,
  loading: storeState.feriados.loading,
  updating: storeState.feriados.updating,
  updateSuccess: storeState.feriados.updateSuccess
});

const mapDispatchToProps = {
  getUnidadeNegocios,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(FeriadosUpdate);
