import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './departamentos.reducer';
import { IDepartamentos } from 'app/shared/model/departamentos.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDepartamentosDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class DepartamentosDetail extends React.Component<IDepartamentosDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { departamentosEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.departamentos.detail.title">Departamentos</Translate> [<b>{departamentosEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="nome">
                <Translate contentKey="agamottoApp.departamentos.nome">Nome</Translate>
              </span>
            </dt>
            <dd>{departamentosEntity.nome}</dd>
            <dt>
              <Translate contentKey="agamottoApp.departamentos.unidadeNegocios">Unidade Negocios</Translate>
            </dt>
            <dd>{departamentosEntity.unidadeNegocios ? departamentosEntity.unidadeNegocios.razaoSocial : ''}</dd>
          </dl>
          <Button tag={Link} to="/departamentos" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/departamentos/${departamentosEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ departamentos }: IRootState) => ({
  departamentosEntity: departamentos.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DepartamentosDetail);
