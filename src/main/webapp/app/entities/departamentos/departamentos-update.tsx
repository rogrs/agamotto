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
import { getEntity, updateEntity, createEntity, reset } from './departamentos.reducer';
import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDepartamentosUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DepartamentosUpdate = (props: IDepartamentosUpdateProps) => {
  const [unidadeNegociosId, setUnidadeNegociosId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { departamentosEntity, unidadeNegocios, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/departamentos');
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
        ...departamentosEntity,
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
          <h2 id="agamottoApp.departamentos.home.createOrEditLabel">
            <Translate contentKey="agamottoApp.departamentos.home.createOrEditLabel">Create or edit a Departamentos</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : departamentosEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="departamentos-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="departamentos-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nomeLabel" for="departamentos-nome">
                  <Translate contentKey="agamottoApp.departamentos.nome">Nome</Translate>
                </Label>
                <AvField
                  id="departamentos-nome"
                  type="text"
                  name="nome"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="departamentos-unidadeNegocios">
                  <Translate contentKey="agamottoApp.departamentos.unidadeNegocios">Unidade Negocios</Translate>
                </Label>
                <AvInput id="departamentos-unidadeNegocios" type="select" className="form-control" name="unidadeNegocios.id">
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
              <Button tag={Link} id="cancel-save" to="/departamentos" replace color="info">
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
  departamentosEntity: storeState.departamentos.entity,
  loading: storeState.departamentos.loading,
  updating: storeState.departamentos.updating,
  updateSuccess: storeState.departamentos.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(DepartamentosUpdate);
