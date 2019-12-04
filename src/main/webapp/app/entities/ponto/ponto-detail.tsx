import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './ponto.reducer';
import { IPonto } from 'app/shared/model/ponto.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPontoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PontoDetail extends React.Component<IPontoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pontoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="agamottoApp.ponto.detail.title">Ponto</Translate> [<b>{pontoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="marcacao">
                <Translate contentKey="agamottoApp.ponto.marcacao">Marcacao</Translate>
              </span>
            </dt>
            <dd>{pontoEntity.marcacao}</dd>
            <dt>
              <span id="motivo">
                <Translate contentKey="agamottoApp.ponto.motivo">Motivo</Translate>
              </span>
            </dt>
            <dd>{pontoEntity.motivo}</dd>
            <dt>
              <Translate contentKey="agamottoApp.ponto.controlePonto">Controle Ponto</Translate>
            </dt>
            <dd>{pontoEntity.controlePonto ? pontoEntity.controlePonto.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/ponto" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/ponto/${pontoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ ponto }: IRootState) => ({
  pontoEntity: ponto.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PontoDetail);
